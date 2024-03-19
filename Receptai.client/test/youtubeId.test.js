import youtube_parser from "../typescript/youtubeId.ts";
import { expect, test } from "vitest";

test("non youtube link returns null", () => {
  expect(youtube_parser("https://google.com")).toBe(null);
});

test("default youtube link without id returns null", () => {
  expect(youtube_parser("https://youtube.com")).toBe(null);
});

test("youtube video link with id returns video id", () => {
  expect(youtube_parser("https://youtu.be/Gw6z32nF69U")).toBe("Gw6z32nF69U");
});

test("youtube video link with other parametrers like chanel, watch id returns only id", () => {
  expect(
    youtube_parser(
      "https://www.youtube.com/watch?v=R7vVEH0kQMA&ab_channel=InsiderFood"
    )
  ).toBe("R7vVEH0kQMA");
});

test("only youtube video id returns null", () => {
  expect(youtube_parser("Gw6z32nF69U")).toBe(null);
});
