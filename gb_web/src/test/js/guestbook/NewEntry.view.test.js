describe ("NewEntry View Tests", function() {
	var sut = sap.ui.jsview("guestbook.NewEntry"); 

	it("should be associated with a controller named 'guestbook.NewEntry'", function() {
		expect(sut.getControllerName()).toBe("guestbook.NewEntry");
	});
});
