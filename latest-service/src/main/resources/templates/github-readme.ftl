# 版本递

##### 数据来源:sonatype、github

### 近期更新
---
<#--
##### *统计于 ${lastUpdated?string('yyyy-MM-dd HH:mm:ss')}*
-->
repo | releases | star
---|---|---
<#list latestList?sort_by(["starCount"])?reverse as latest>
[${latest.name}](https://github.com/${latest.fullName}) | ${latest.version} | ${latest.starCount?c}
</#list>

### 收录列表
---
repo | releases | star
---|---|---
<#list include?sort_by(["starCount"])?reverse as fav>
[${fav.name}](https://github.com/${fav.fullName}) | ${fav.lastVersion} | ${fav.starCount?c} 
</#list>

<#include "/github-readme-footer.ftl"/>