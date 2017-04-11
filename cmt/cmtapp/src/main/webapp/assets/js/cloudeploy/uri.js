var rootURI = function() {
	return "/cmt";
};

var dURIs = {
	appURI : rootURI() + "/v2/artifact",
	categoryURI : rootURI() + "/v2/artifact/category",
	searchURI : rootURI() + "/v2/artifact/query",
	analyzeURI : rootURI() + "/v2/analyze",
	domainURI : rootURI() + "/v2/domains",
	hostURI : rootURI() + "/v2/hosts",
	customFilesURI : rootURI() + "/v2/resources/files/custom",
	filesURI : rootURI() + "/v2/files",
	templateURI : rootURI() + "/v2/templates",
	viewsURI : {
		artList : rootURI() + "/v2/views/artifact/list",
		artSearch : rootURI() + "/v2/views/artifact/search",
		artAnalyze : rootURI() + "/v2/views/artifact/analyze",
		artUpload : rootURI() + "/v2/views/artifact/upload",
		configManager: rootURI() + "/v2/views/config",
		artDetail : rootURI() + "/v2/views/artifact/detail"
	},
	swfs : rootURI() + "/swf",
};