<template>
    <div>{{ testpaperTitle }}</div>
</template>

<script lang="ts" setup>
import { useRoute } from 'vue-router';
import $ from 'jquery';
import { useUserStore } from '@/store/user';
const route = useRoute();
const testpaperTitle = decodeURIComponent(route.query.title as string)

function getCheckMsg()  {
    $.ajax({
        url: 'http://127.0.0.1:3000/get/check/msg/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        data: {
            testpaper_title: testpaperTitle,
        },
        success: (result:string) => {
            const resp = JSON.parse(result);
            console.log(resp);
        },
        error: () => {
            console.log("error");
        }
    })
}

getCheckMsg()
</script>

<style scoped>
</style>