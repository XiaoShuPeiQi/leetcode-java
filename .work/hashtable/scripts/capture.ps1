param([Parameter(Mandatory=$true)][string]$JdkPath)
$ErrorActionPreference='Stop'
$repoPath=(Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '../../..')).Path
Push-Location -LiteralPath $repoPath
try {
    New-Item -ItemType Directory -Path 'bin','.work/hashtable/tmp' -Force | Out-Null
    $javaSources=@(Get-ChildItem -LiteralPath 'src/main/java/leetcode/hashtable' -Filter '*.java' | ForEach-Object FullName)
    & (Join-Path $JdkPath 'bin/javac.exe') --release 17 -encoding UTF-8 -g -d bin '.work/hashtable/scripts/HashTraceRecorder.java' @javaSources
    if($LASTEXITCODE -ne 0){throw 'Compilation failed'}
    $catalog=Get-Content -LiteralPath 'imports/哈希表.import.json' -Raw -Encoding UTF8 | ConvertFrom-Json
    foreach($problem in $catalog.problems){
        & (Join-Path $JdkPath 'bin/java.exe') --add-modules jdk.jdi '-Dfile.encoding=UTF-8' -cp bin HashTraceRecorder $problem.mainClass ('.work/hashtable/tmp/trace-'+$problem.id+'.ndjson') $JdkPath
        if($LASTEXITCODE -ne 0){throw ('Recording failed: '+$problem.id)}
    }
} finally {Pop-Location}
