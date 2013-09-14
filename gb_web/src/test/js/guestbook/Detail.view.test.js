describe("The detail view", function() {
	var sut = sap.ui.jsview("guestbook.Detail");

	beforeEach(function() {
		var model = new sap.ui.model.json.JSONModel([ {
			name : "Name Entry",
			text : "Text Entry",
			date : "Sep 14, 2013 9:03:24 AM"
		} ]);

		list = sut.byId("EntryItemList");
		list.setModel(model);
		list.bindElement("/0");
	});

	it("should be associated with a controller named 'guestbook.Detail'",
			function() {
				expect(sut.getControllerName()).toBe("guestbook.Detail");
			});

	it("should be display the test of the entry", function() {
		expect(sut.byId("EntryItemList")).toBeDefined();
	});

	it("should display the detailed entry", function() {
		expect(list.getItems().length).toBe(3);
	});

	it("should display the name, date and text", function() {
		expect(list.getItems()[0].getTitle()).toBe("Name Entry");
		expect(list.getItems()[1].getTitle()).toBe("Sep 14, 2013 9:03:24 AM");
		expect(list.getItems()[2].getTitle()).toBe("Text Entry");
	});

});