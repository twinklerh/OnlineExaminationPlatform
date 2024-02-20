<template>
    <div>{{ problem }}</div>
    <el-card class="el-card">
        <h1 style="width=1090px">题目详情</h1>
        <el-divider border-style="dashed" style="width=100%"/>
    </el-card>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { useRoute } from 'vue-router';
import $ from 'jquery'
import { ref } from 'vue';

const userStore = useUserStore();
const route = useRoute();
const problem_id = route.query.id;
let problem = ref({});

$.ajax({
    url: 'http://localhost:3000/getaproblem/',
    type: 'get',
    headers: {
        Authorization: 'Bearer ' + userStore.token,
    },
    data: {
        problem_id: problem_id
    },
    success: (resp:string)=>{
        problem.value = JSON.parse(resp);
        console.log(problem.value);
    },
    error: ()=>{
        alert("失败");
    }
})


</script>

<style scoped>
.el-card{
    margin: auto auto;
    width: 80%;
}
</style>