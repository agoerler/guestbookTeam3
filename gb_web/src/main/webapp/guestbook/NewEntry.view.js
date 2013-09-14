sap.ui.jsview("guestbook.NewEntry", {

	getControllerName : function() {
		return "guestbook.NewEntry";
	},

	createContent : function(oController) {
		var oPage = new sap.m.Page({
			title : "Create New Entry",
			showNavButton: true,
			navButtonText: "Cancel",
			navButtonPress: function(){ 
		          app.back();            // when pressed, the back button should navigate back up to page 1
		      }
		});

		
		var vBox = new sap.m.VBox({
			items: [
			        new sap.m.Label({
			        	text: "Name"
			        }),
			        new sap.m.Input("inputName", {
			        	type: sap.m.InputType.Text,
			        	placeholder: "Enter your name",
			        	value: "{/newName}"
			        }),
			        new sap.m.Label({
			        	text: "Message"
			        }),
			        new sap.m.Input("inputMessage", {
			        	type: sap.m.InputType.TextArea,
			        	placeholder: "Enter your message",
			        	value: "{/newMessage}"
			        }),
			        new sap.m.Button("submitButton", {
			        	text: "Submit",
			        	width: "100%",
			        	press: [oController.submitButtonPressed, oController]
			        })
			        ]
		});
		
		// adding some space to the elements to make it look nicer
		vBox.addStyleClass("entryForm");
		
		
        oPage.addContent(vBox);
		
		return oPage;
	}

});
