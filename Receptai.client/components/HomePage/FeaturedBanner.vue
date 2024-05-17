<template>
  <div class="relative w-full h-80 shadow-md">
    <div class="z-[5] rounded-md absolute top-0 left-0 w-full h-full bg-[#00000094] flex justify-center items-center">
        <div class="flex justify-center items-center flex-col gap-2 mx-10 my-5">
            <h1 class="text-white font-semibold text-5xl">Tasty Bytes</h1>
            <p class="text-white text-center leading-5">
                Craving delicious meals but short on time? Look no further than Tasty Bytes! We offer a mouthwatering collection of recipes designed for busy home cooks. From creative vegetarian options to hearty classics, our recipes are easy to follow and use everyday ingredients. Get ready to spice up your life with Tasty Bytes!
            </p>
        </div>
    </div>
    <div
      :class="`absolute top-0 left-0 rounded-md w-full h-full bg-gray-500 border-black border-2 bg-cover bg-center bg-[url('/images/longevity-foods.webp')]`"
    ></div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped></style>
