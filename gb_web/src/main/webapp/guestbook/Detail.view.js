sap.ui.jsview("guestbook.Detail", {

	getControllerName : function() {
		return "guestbook.Detail";
	},

	createContent : function(oController) {
		return new sap.m.Page({
			title : "Guestbook",
			showNavButton : true,
			navButtonPress : [ oController.onBack, oController ],
			content : [ new sap.m.List({
				id : oController.createId("EntryItemList")
			}) ]
		});
	}

});
