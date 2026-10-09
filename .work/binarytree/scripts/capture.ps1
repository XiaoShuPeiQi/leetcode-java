param([int[]]$ProblemIds = @())
$ErrorActionPreference = 'Stop'
$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot '../../..')).Path
Push-Location $projectRoot
try {
    New-Item -ItemType Directory -Force -Path '.work/binarytree/tmp/classes','.work/binarytree/logs' | Out-Null
    & '.tools/jdk-17/bin/javac.exe' --add-modules jdk.jdi -encoding UTF-8 -g -d '.work/binarytree/tmp/classes' '.work/binarytree/scripts/TreeTraceRecorder.java'
    if ($LASTEXITCODE -ne 0) { throw 'JDI 编译失败' }
    $metadata = Get-Content -LiteralPath 'imports/二叉树算法.import.json' -Raw | ConvertFrom-Json
    foreach ($problem in @($metadata.problems | Where-Object { $ProblemIds.Count -eq 0 -or $ProblemIds -contains $_.id })) {
        & '.tools/jdk-17/bin/java.exe' --add-modules jdk.jdi -cp '.work/binarytree/tmp/classes' TreeTraceRecorder "leetcode.binarytree.$($problem.className)" ".work/binarytree/tmp/trace-$($problem.id).ndjson" '.tools/jdk-17'
        if ($LASTEXITCODE -ne 0) { throw "第 $($problem.id) 题采集失败" }
    }
} finally { Pop-Location }
