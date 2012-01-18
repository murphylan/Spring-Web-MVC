<#macro pages url="">
<#assign pageNo=page.getPageNo()?default('1') />
<form id="filterPageForm" name="filterPageForm" method="post" action="<#if url=="">${request.getRequestUri()}<#else>${url}</#if>" onsubmit="return checkForm()">
<input name="pageNo" id="pageNo" value="${pageNo}" type="hidden" />
<#list RequestParameters?default({})?keys as list>
<#if list!='pageNo'>
	<input type="hidden" name="${list}" id="${list}" value="${RequestParameters[list]?default('')}"/>
	</#if>
</#list>
</form>
<script language="javascript">
    function filterPageForm_submit(page){
    	if(page){
        	document.getElementById("pageNo").value=page;
    	}
    	
	    document.filterPageForm.submit();
    }
</script>
Current page: ${pageNo}, Total: ${page.getTotalPages()?default('')} &nbsp;&nbsp;
		<a href="javascript:filterPageForm_submit(1);">First</a>
		<#if (pageNo?number-3>0)>
			...&nbsp;
		</#if>
		<#if (pageNo?number-2>0)>
			<a href="javascript:filterPageForm_submit(${pageNo-2});">${pageNo?default('0')?number-2}</a>&nbsp;
		</#if>
		<#if (pageNo?number-1>0)>
			<a href="javascript:filterPageForm_submit(${pageNo-1});">${pageNo-1}</a>&nbsp;
		</#if>
		<#if (pageNo?number>0)>
			${pageNo}&nbsp;
		</#if>
		<#if (page.getTotalPages()?default('0')?number>=pageNo?number+1)>
			<a href="javascript:filterPageForm_submit(${pageNo+1});">${pageNo+1}</a>&nbsp;
		</#if>
		<#if (page.getTotalPages()?default('0')?number>=pageNo?number+2)>
			<a href="javascript:filterPageForm_submit(${pageNo+2});">${pageNo+2}</a>&nbsp;
		</#if>
		<#if (page.getTotalPages()?default('0')?number>=pageNo?number+3)>
			<a href="javascript:filterPageForm_submit(${pageNo+3});">${pageNo+3}</a>&nbsp;
		</#if>
		<#if (page.getTotalPages()?default('0')?number>=pageNo?number+4)>
			<a href="javascript:filterPageForm_submit(${pageNo+4});">${pageNo+4}</a>&nbsp;
		</#if>
		<#if (page.getTotalPages()?default('0')?number>=pageNo?number+5)>
			...&nbsp;
		</#if>
		<a href="javascript:filterPageForm_submit(${page.getTotalPages()});" >Last</a>
</#macro>

<#assign base=springMacroRequestContext.getContextUrl("")>

<#-- tag -->
<#macro bind path>
    <#if htmlEscape?exists>
        <#assign status = springMacroRequestContext.getBindStatus(path, htmlEscape)>
    <#else>
        <#assign status = springMacroRequestContext.getBindStatus(path)>
    </#if>
    <#-- assign a temporary value, forcing a string representation for any
    kind of variable. This temp value is only used in this macro lib -->
    <#if status.value?exists && status.value?is_boolean>
        <#assign stringStatusValue=status.value?string>
    <#else>
        <#assign stringStatusValue=status.value?default("")>
    </#if>
</#macro>
<#function contains list item>
    <#list list as nextInList>
    <#if nextInList == item><#return true></#if>
    </#list>
    <#return false>
</#function>

<#macro formCheckboxes path options separator="" attributes="">
    <@bind path/>
    <#list options?keys as value>
    <#assign a="(value?size)">
    <#assign id="${status.expression}${value_index}">
    <#assign isSelected = contains(status.value?default("")?split(","), value)>	
    <div style="float:left; width:100px"">    
    <input type="checkbox" id="${id}" name="${status.expression}" value="${value?html}"<#if isSelected> checked="checked"</#if> ${attributes}<@closeTag/>
    <label for="${id}">${options[value]?html}</label>${separator}
    </div>
    </#list>
    <input type="hidden" name="_${status.expression}" value="on"/>
</#macro>
<#macro closeTag>
    <#if xhtmlCompliant?exists && xhtmlCompliant>/><#else>></#if>
</#macro>
