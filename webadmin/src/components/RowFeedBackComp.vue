<template>
    <div class="container" @click="getContent">
        <span style="margin-left:150px;">
            {{ displayData.id }}、
        </span>
        <span :class="displayData.isRead ==='未处理'?'unread':'read'">{{ displayData.isRead }}</span>
    </div>
</template>

<script lang="ts" setup>
import { Feedback, useUserStore } from '@/store/user';
import axios from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';
import { defineProps, ref } from 'vue';
const props = defineProps({
    feedback: {
        type: Object as ()=>Feedback
    }
})
const displayData = ref({
    id: props.feedback?.feedbackId,
    content: props.feedback?.content,
    isRead: props.feedback?.isRead ? '已处理' : '未处理',
})

function getContent()   {
    ElMessageBox.confirm(displayData.value.content,'内容详情', {
        confirmButtonText: '处理',
    }).then(()=>{
        axios.post('http://127.0.0.1:3000/admin/read/feedback/',{
            feedbackId: displayData.value.id,
        }, {
            headers: {
                Authorization: 'Bearer ' + useUserStore().token
            }
        }).then((response)=>{
            const resp = response.data;
            if(resp.error_message === 'success')    displayData.value.isRead = '已处理';
            else    ElMessage.error("失败");
        })
    }).catch(()=>{return;})
}
</script>


<style scoped>
.container {
    border: solid #AFEEEE;
    border-radius: 10px;
    width: 850px;
    height: 30px;
}
.container:hover {
    background-color: #AFEEEE;
    cursor: pointer;
}
.unread {
    color: red;
}
.read {
    color: black;
}
</style>