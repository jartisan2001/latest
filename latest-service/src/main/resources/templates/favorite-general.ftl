# ${favorite.name} <#list github.releases as release>${release}<#if release_has_next>,</#if></#list>

---

**GitHub Watch <font color=#FF0000>${github.watcherCount}</font>，Star <font color=#FF0000>${github.starCount}</font>，Fork <font color=#FF0000>${github.forkCount}</font>**

> 开发语言 <font color=#FF0000>${favorite.language}</font>

> 开源协议 <font color=#FF0000> Apache 2.0 license.</font>

> 上一次更新日期：${github.lastUpdated?string('yyyy-MM-dd')}，项目创建日期：${github.createdDate?string('yyyy-MM-dd')}


