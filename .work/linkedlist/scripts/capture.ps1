param([Parameter(Mandatory=$true)][string]$JdkPath)
$ErrorActionPreference = 'Stop'
$repoPath = (Get-Location).Path
$jdkFullPath = (Resolve-Path -LiteralPath $JdkPath).Path
$javacPath = Join-Path $jdkFullPath 'bin\javac.exe'
$javaPath = Join-Path $jdkFullPath 'bin\java.exe'
if (-not (Test-Path -LiteralPath $javacPath)) { throw 'javac not found' }
$metadata = Get-Content -LiteralPath 'imports\链表.import.json' -Raw -Encoding UTF8 | ConvertFrom-Json
New-Item -ItemType Directory -Path 'bin','.work\linkedlist\tmp' -Force | Out-Null
$sources = @('src/main/java/common/ListNode.java', '.work/linkedlist/scripts/LinkedTraceRecorder.java')
foreach ($problem in $metadata.problems) { $sources += $problem.javaFile }
& $javacPath --release 17 -encoding UTF-8 -g -d bin $sources
if ($LASTEXITCODE -ne 0) { throw 'javac failed' }
foreach ($problem in $metadata.problems) {
    $tracePath = '.work/linkedlist/tmp/trace-' + $problem.id + '.ndjson'
    & $javaPath --add-modules jdk.jdi '-Dfile.encoding=UTF-8' -cp bin LinkedTraceRecorder $problem.mainClass $tracePath $jdkFullPath
    if ($LASTEXITCODE -ne 0) { throw ('trace failed: ' + $problem.id) }
}
