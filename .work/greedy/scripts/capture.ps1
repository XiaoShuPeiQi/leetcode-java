param([Parameter(Mandatory=$true)][string]$JdkPath)
$ErrorActionPreference = 'Stop'
$repoPath = (Get-Location).Path
$javacPath = Join-Path $JdkPath 'bin\javac.exe'
$javaPath = Join-Path $JdkPath 'bin\java.exe'
if (-not (Test-Path -LiteralPath $javacPath)) { throw 'javac not found' }
$metadata = Get-Content -LiteralPath 'imports\贪心算法.import.json' -Raw -Encoding UTF8 | ConvertFrom-Json
New-Item -ItemType Directory -Path 'bin','.work\greedy\tmp' -Force | Out-Null
$sources = Get-ChildItem -LiteralPath 'src\main\java' -Filter '*.java' -Recurse | Select-Object -ExpandProperty FullName
$sources += Join-Path $repoPath '.work\greedy\scripts\GreedyTraceRecorder.java'
& $javacPath --release 17 -encoding UTF-8 -g -Xlint:all -d bin $sources
if ($LASTEXITCODE -ne 0) { throw 'javac failed' }
foreach ($problem in $metadata.problems) {
    $tracePath = '.work/greedy/tmp/trace-' + $problem.id + '.ndjson'
    & $javaPath --add-modules jdk.jdi -Dfile.encoding=UTF-8 -cp bin GreedyTraceRecorder $problem.mainClass $tracePath $JdkPath
    if ($LASTEXITCODE -ne 0) { throw ('trace failed: ' + $problem.id) }
}
