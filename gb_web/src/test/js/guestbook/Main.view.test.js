describe("the view", function() {
	var sut = sap.ui.jsview("guestbook.Main");
	var list;

	beforeEach(function() {
		var model = new sap.ui.model.json.JSONModel([ {
			name : "Name Entry",
			text : "Text Entry",
			date : "Sep 14, 2013 9:03:24 AM"
		} ]);

		list = sut.byId("EntryList");
		list.setModel(model);
	});

	it("should be associated with a controller named 'guestbook.Main'",
			function() {
				expect(sut.getControllerName()).toBe("guestbook.Main");
			});

	it("should be a list element on the view", function() {
		expect(sut.byId("EntryList")).toBeDefined();
	});

	it("should display at least one entry", function() {
		expect(list.getItems().length).toBe(1);
	});

	it("should display the name and the date", function() {
		expect(list.getItems().length).toBe(1);

		var item = list.getItems()[0];

		expect(item.getTitle()).toBe("Name Entry");
		expect(item.getDescription()).toBe("Sep 14, 2013 9:03:24 AM");
	});
});