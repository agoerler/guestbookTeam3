/*global sap */
sap.ui.controller("guestbook.NewEntry", {

	/**
	 * Called when a controller is instantiated and its View controls (if
	 * available) are already created. Can be used to modify the View before it
	 * is displayed, to bind event handlers and do other one-time
	 * initialization.
	 */
	onInit : function() {
		this.model = new sap.ui.model.json.JSONModel({
			display : 0
		});
		this.startNext = true;
		sap.ui.getCore().setModel(this.model);
	},

	/**
	 * Similar to onAfterRendering, but this hook is invoked before the
	 * controller's View is re-rendered (NOT before the first rendering!
	 * onInit() is used for that one!).
	 */
	// onBeforeRendering: function() {
	//
	// },
	/**
	 * Called when the View has been rendered (so its HTML is part of the
	 * document). Post-rendering manipulations of the HTML could be done here.
	 * This hook is the same one that SAPUI5 controls get after being rendered.
	 */
	// onAfterRendering: function() {
	//
	// },
	/**
	 * Called when the Controller is destroyed. Use this one to free resources
	 * and finalize activities.
	 */
	// onExit: function() {
	//
	// },

	/**
	 * This action is called when the submit button in the new entry view is pressed
	 * It consumes the data binded within the textfields
	 */
	submitButtonPressed : function(oControlEvent) {

		var obj = {
				"name": this.model.getProperty("/newName"),
				"text": this.model.getProperty("/newMessage")
		};
		
		submitEntry(JSON.stringify(obj));
	},
	
	/**
	 * Calls the POST method on the database in order to save the new entry
	 * @param json JSON string containing the name and text elements
	 */
	submitEntry : function(json) {
		// Call the POST method
	}

});
