sap.ui.jsview("guestbook.Main", {

	getControllerName : function() {
		return "guestbook.Main";
	},

	createContent : function(oController) {
		return new sap.m.Page({
			title : "Guestbook",
			content : [ new sap.m.List({
				id : oController.createId("EntryList"),

			}) ],
			headerContent : [ new sap.m.Button({
				press : [ oController.onCreateNew, oController ],
				text : "create new entry"
			}) ]
		});
	}

});
