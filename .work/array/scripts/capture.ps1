param([Parameter(Mandatory = $true)][string]$JdkPath)
$ErrorActionPreference = 'Stop'
$repoRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '../../..')).Path
$jdkRoot = (Resolve-Path -LiteralPath $JdkPath).Path
Push-Location -LiteralPath $repoRoot
try {
    $metadata = Get-Content -LiteralPath 'imports/数组.import.json' -Raw -Encoding utf8 | ConvertFrom-Json
    New-Item -ItemType Directory -Path 'bin', '.work/array/tmp' -Force | Out-Null
    $sources = @($metadata.problems | ForEach-Object { $_.java_file })
    $sources += (Join-Path $PSScriptRoot 'ArrayTraceRecorder.java')
    & (Join-Path $jdkRoot 'bin/javac.exe') --release 17 -encoding UTF-8 -g -d bin $sources
    if ($LASTEXITCODE -ne 0) { throw 'Java compilation failed' }
    foreach ($problem in $metadata.problems) {
        & (Join-Path $jdkRoot 'bin/java.exe') --add-modules jdk.jdi '-Dfile.encoding=UTF-8' -cp bin ArrayTraceRecorder $problem.mainClass ".work/array/tmp/trace-$($problem.id).ndjson" $jdkRoot
        if ($LASTEXITCODE -ne 0) { throw "JDI recording failed: $($problem.id)" }
    }
} finally {
    Pop-Location
}
