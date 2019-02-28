# 版本递

### 今日更新
---
<#--
##### *统计于 ${lastUpdated?string('yyyy-MM-dd HH:mm:ss')}*
-->
repo | releases | star
---|---|---
<#list latestList?sort_by(["starCount"])?reverse as latest>
${latest.name} | ${latest.version} | ${latest.starCount?c}
</#list>

### 收录列表
---
repo | releases | star
---|---|---
<#list include?sort_by(["starCount"])?reverse as fav>
${fav.name} | ${fav.lastVersion} | ${fav.starCount?c} 
</#list>

<#include "/github-readme-footer.ftl"/>