# 重构之美
![Spring Boot 2.0](https://img.shields.io/badge/Spring%20Boot-2.0-brightgreen.svg)
![Thymeleaf 3.0](https://img.shields.io/badge/Thymeleaf-3.0-yellow.svg)
![Mysql 5.6](https://img.shields.io/badge/Mysql-5.6-blue.svg)
![JDK 1.8](https://img.shields.io/badge/JDK-1.8-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-3.5.0-yellowgreen.svg)
![license](https://img.shields.io/badge/license-Apache%202-blue.svg)
##### 数据来源:sonatype、github

<#include "/github-readme-footer.ftl"/>

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


### 重构之美的订阅号
---
<img src="https://github.com/jartisan2001/latest/blob/master/Image.jpg" width="400" hegiht="400" align=left />
