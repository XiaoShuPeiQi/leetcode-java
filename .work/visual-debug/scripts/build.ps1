param(
    [int[]]$ProblemIds = @(),
    [string]$JdkHome = '.tools/jdk-17'
)
$ErrorActionPreference = 'Stop'
$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot '../../..')).Path
Push-Location $projectRoot
try {
    $jdkPath = (Resolve-Path $JdkHome).Path
    $javac = Join-Path $jdkPath 'bin/javac.exe'
    $java = Join-Path $jdkPath 'bin/java.exe'
    New-Item -ItemType Directory -Path 'bin','.work/visual-debug/tmp/classes','.work/visual-debug/logs','.work/visual-debug/previews' -Force | Out-Null
    $sources = @(Get-ChildItem 'src/main/java' -Recurse -Filter '*.java' | Select-Object -ExpandProperty FullName)
    & $javac --release 17 -encoding UTF-8 -g -d bin $sources
    if ($LASTEXITCODE -ne 0) { throw '项目源码编译失败' }
    & $javac --add-modules jdk.jdi -encoding UTF-8 -g -d '.work/visual-debug/tmp/classes' '.work/visual-debug/scripts/TraceRecorder.java'
    if ($LASTEXITCODE -ne 0) { throw '追踪器编译失败' }
    $metadata = Get-Content 'imports/回溯算法.import.json' -Raw -Encoding UTF8 | ConvertFrom-Json
    $problems = @($metadata.problems | Where-Object { $ProblemIds.Count -eq 0 -or $ProblemIds -contains $_.id } | Sort-Object @{Expression={if ($_.id -eq 131) {0} else {1}}},id)
    if ($ProblemIds.Count -gt 0 -and $problems.Count -ne $ProblemIds.Count) { throw '题号不存在或重复' }
    foreach ($problem in $problems) {
        $className = [System.IO.Path]::GetFileNameWithoutExtension($problem.java_file)
        & $java --add-modules jdk.jdi -cp '.work/visual-debug/tmp/classes' TraceRecorder "leetcode.backtracking.$className" ".work/visual-debug/tmp/trace-$($problem.id).ndjson" $jdkPath
        if ($LASTEXITCODE -ne 0) { throw "第 $($problem.id) 题追踪失败" }
    }
    $selected = @($problems | ForEach-Object { [string]$_.id })
    & node '.work/visual-debug/scripts/build.mjs' @selected
    if ($LASTEXITCODE -ne 0) { throw '网页数据生成失败' }
    & node '.work/visual-debug/scripts/verify.mjs'
    if ($LASTEXITCODE -ne 0) { throw '轨迹核验失败' }
} finally {
    Pop-Location
}
