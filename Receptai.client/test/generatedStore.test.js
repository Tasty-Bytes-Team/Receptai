import { addNotification, resetNotification, store } from "@/store/store.ts";

describe("Notification Store Tests", () => {
  beforeEach(() => {
    resetNotification(); // Ensure the store is reset before each test
  });

  it("test_addNotification_updates_store_correctly", () => {
    const links = [
      { text: "Link1", link: "http://example.com", type: "Black" },
    ];
    addNotification("Test Message", "Error", links);
    expect(store.text).toBe("Test Message");
    expect(store.show).toBe(true);
    expect(store.label).toBe("Error");
    expect(store.links).toEqual(links);
  });

  it("test_resetNotification_resets_store_correctly", () => {
    // First, add a notification to change the initial state
    addNotification("Test Message", "Error");
    // Then, reset the notification
    resetNotification();
    expect(store.text).toBe("");
    expect(store.show).toBe(false);
    expect(store.label).toBeUndefined();
    expect(store.links).toBeUndefined();
  });

  it("test_addNotification_handles_missing_parameters_correctly", () => {
    addNotification("Test Message");
    expect(store.text).toBe("Test Message");
    expect(store.show).toBe(true);
    expect(store.label).toBeUndefined();
    expect(store.links).toBeUndefined();
  });
});
