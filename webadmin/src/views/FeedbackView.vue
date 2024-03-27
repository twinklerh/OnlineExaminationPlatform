<template>
    <div>
        <div v-for="(item,index) in feedbackList" :key="index">
            <RowFeedBackComp :feedback="item" style="margin-top:10px;"/>
        </div>
    </div>
</template>

<script lang="ts" setup>
import RowFeedBackComp from '@/components/RowFeedBackComp.vue';
import { Feedback, useUserStore } from '@/store/user';
import axios from 'axios';
import { ref } from 'vue';
const feedbackList = ref<Feedback[]>([]);

function getFeedback() {
    axios.post('http://127.0.0.1:3000/admin/get/feedback/', { currentPage: 1 }, {
        headers: {
            Authorization: 'Bearer ' + useUserStore().token,
        }
    }).then((response)=>{
        const data = response.data;
        if(data.error_message === 'success')    feedbackList.value = JSON.parse(data.feedbackList)
    })   
}
getFeedback();
</script>

<style scoped>
</style>