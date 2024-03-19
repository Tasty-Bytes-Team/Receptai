import { expect, test } from "vitest";
import { renderSuspended } from '@nuxt/test-utils/runtime'
import RecipeContainerComponent from '../components/RecipeContainerComponent/RecipeContainerComponent.vue';
import { screen } from '@testing-library/vue'

test('renders a notification', async () => {
    const wrapper = await renderSuspended(RecipeContainerComponent, {
        props: {
            imageLink: "https://images.food52.com/jgWjO2TMvaNaXzy9ME4PBYV5jyA=/1930x1286/filters:format(webp)/906a901f-6dad-452a-803e-3087c35e3de6--2013-0820_gena_veggie-burgers-062.jpg",
            name: "Spicy Black Bean and Corn Burgers",
            raiting: 4.5,
            about: "Vegetarian burgers packed with black beans, corn, and a touch of spice.",
            link: "/recipes/15",
            category: "Burgers",
            categoryLink: "/category/burgers",
            prepTime: 10,
        }
    })
    
    console.log(wrapper.html())
//     expect(wrapper).toMatchInlineSnapshot(`
//     "<div id="test-wrapper">
//       <div>This is an auto-imported component</div>
//       <div> I am a global component </div>
//       <div>Index page</div><a href="/test"> Test link </a>
//     </div>"
//   `)
})