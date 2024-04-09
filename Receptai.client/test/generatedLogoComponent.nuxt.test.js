import { describe, it, expect, vi } from "vitest";
import { mount } from "@vue/test-utils";
import YourComponent from "@/components/Header/components/Logo.vue";

export const mockNuxtLink = {
  template: "<a :href='to'><slot /></a>",
  props: {
    to: {
      type: String,
      required: true,
    },
  },
};

describe("Logo component tests", () => {
  it('renders "Tasty Bytes" for window width greater than 380px', async () => {
    // Mock window.innerWidth
    Object.defineProperty(window, "innerWidth", {
      writable: true,
      configurable: true,
      value: 400,
    });
    // Trigger the window resize event
    window.dispatchEvent(new Event("resize"));

    const wrapper = mount(YourComponent, {
      global: {
        stubs: {
          NuxtLink: mockNuxtLink,
        },
      },
    });
    await wrapper.vm.$nextTick(); // Wait for Vue to react to state changes

    expect(wrapper.text()).toContain("Tasty Bytes");
  });

  it('renders "TB" for window width less than or equal to 380px', async () => {
    // Adjust window.innerWidth for the second test
    Object.defineProperty(window, "innerWidth", {
      writable: true,
      configurable: true,
      value: 380,
    });
    // Trigger the window resize event again
    window.dispatchEvent(new Event("resize"));

    const wrapper = mount(YourComponent, {
      global: {
        stubs: {
          NuxtLink: mockNuxtLink,
        },
      },
    });
    await wrapper.vm.$nextTick(); // Wait for Vue to react to state changes

    expect(wrapper.text()).toContain("TB");
  });
});
