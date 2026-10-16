param([Parameter(Mandatory = $true)][string]$JdkPath)
$ErrorActionPreference = 'Stop'
$repoRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '../../..')).Path
$jdkRoot = (Resolve-Path -LiteralPath $JdkPath).Path
$javacPath = Join-Path $jdkRoot 'bin/javac.exe'
$javaPath = Join-Path $jdkRoot 'bin/java.exe'
Push-Location -LiteralPath $repoRoot
try {
    $metadata = Get-Content -LiteralPath 'imports/动态规划.import.json' -Raw -Encoding utf8 | ConvertFrom-Json
    New-Item -ItemType Directory -Path 'bin', '.work/dp/tmp' -Force | Out-Null
    $sources = @(Get-ChildItem -LiteralPath 'src/main/java' -Recurse -Filter '*.java' | Select-Object -ExpandProperty FullName)
    $sources += (Join-Path $PSScriptRoot 'DPTraceRecorder.java')
    & $javacPath --release 17 -encoding UTF-8 -g -d bin $sources
    if ($LASTEXITCODE -ne 0) { throw 'Java compilation failed' }
    foreach ($problem in $metadata.problems) {
        $recordArgs = @('--add-modules', 'jdk.jdi', '-Dfile.encoding=UTF-8', '-cp', 'bin', 'DPTraceRecorder', $problem.mainClass, ".work/dp/tmp/trace-$($problem.id).ndjson", $jdkRoot)
        if ($problem.solutionClass -eq 'DPSolution') { $recordArgs += 'DPSolution' }
        & $javaPath $recordArgs
        if ($LASTEXITCODE -ne 0) { throw "JDI recording failed: $($problem.id)" }
    }
} finally {
    Pop-Location
}
