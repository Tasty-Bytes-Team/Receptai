import { store, addNotification, resetNotification } from "@/store/store.ts";
import { expect, test } from "vitest";

test("adding message shows up in the global store", () => {
  afterEach(() => {
    resetNotification();
  });

  addNotification("Test");
  expect(store.text).toBe("Test");
  expect(store.show).toBe(true);
});

test("adding message with custom label shows up in the global store", () => {
  addNotification("Testing type", "Error");
  expect(store.text).toBe("Testing type");
  expect(store.label).toBe("Error");
  expect(store.show).toBe(true);
});

test("adding message with custom label and button shows up in the global store", () => {
  addNotification("Testing button", "Success", [
    { text: "Test", link: "testLink" },
  ]);
  expect(store.text).toBe("Testing button");
  expect(store.label).toBe("Success");
  expect(store.links.length).toBe(1);
  expect(store.links[0].text).toBe("Test");
  expect(store.links[0].link).toBe("testLink");
  expect(store.show).toBe(true);
});

test("adding message with multiple buttons shows up in the global store", () => {
  addNotification("Testing multiple button", undefined, [
    { text: "Button1", link: "testLink1" },
    { text: "Button2", link: "testLink2" },
    { text: "Button3", link: "testLink3" },
  ]);
  expect(store.text).toBe("Testing multiple button");
  expect(store.links.length).toBe(3);
  expect(store.links).toStrictEqual([
    { text: "Button1", link: "testLink1" },
    { text: "Button2", link: "testLink2" },
    { text: "Button3", link: "testLink3" },
  ]);
  expect(store.show).toBe(true);
});

test("reseting a notification resets all values", () => {
  addNotification("Testing reset", "Error");
  expect(store.text).toBe("Testing reset");
  expect(store.label).toBe("Error");
  expect(store.show).toBe(true);

  resetNotification();
  expect(store.text).toBe("");
  expect(store.label).toBe(undefined);
  expect(store.show).toBe(false);
});
