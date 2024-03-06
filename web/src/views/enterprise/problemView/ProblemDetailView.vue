<template>
    <el-card class="el-card">
        <el-container>
           <el-header>
                <h1 style="width:1090px">题目详情</h1>
                <el-divider border-style="dashed"/>  
           </el-header>
           <el-container>
                <el-aside width="70%">
                    <h2>{{ problem.id }}.{{ problem.title }}</h2>    
                    <h3 style="margin-left: 20px;">题目描述：</h3>
                    <p style="margin-left: 40px;">{{ problem.description }}</p>
                </el-aside>
                <el-main width="30%">
                    <h3>难度：{{ problem.difficulty }}</h3>
                    <h3>批改类型：{{ problem.checkBy }}</h3>
                    <h3>题型：{{ problem.type }}</h3>
                    <h3>正确答案：{{ problem.rightAnswer }}</h3>
                    <h3>通过次数：{{ problem.accurateTimes }}</h3>
                    <h3>完成次数：{{ problem.finishedTimes }}</h3>            
                </el-main>
            </el-container>
        </el-container>
    </el-card>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { useRoute } from 'vue-router';
import $ from 'jquery'
import { ref } from 'vue';

const userStore = useUserStore();
const route = useRoute();
const problem_id = route.params.problem_id;
interface problem_detail {
    id: number, title: string, description: string, difficulty: string, 
    checkBy: string, type: '', accurateTimes: number, finishedTimes: number, rightAnswer: string,
}
let problem = ref<problem_detail>({
    id: 0, title: '', 
    description:'', difficulty: '', 
    checkBy:'', type: '',
    accurateTimes: 0,finishedTimes:0,
    rightAnswer:''
});

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
        if(problem.value.difficulty==='average')    problem.value.difficulty = '一般';
        else if(problem.value.difficulty==='easy')  problem.value.difficulty = '容易';
        else if(problem.value.difficulty==='difficult')  problem.value.difficulty = '难';
        else if(problem.value.difficulty==='noSet')     problem.value.difficulty = '尚未设置';
        problem.value.checkBy==='mechine' ? problem.value.checkBy = '自动批改' : problem.value.checkBy = '人工批改';

    },
    error: ()=>{
        console.log(67, problem_id);
        alert("失败");
    }
})


</script>

<style scoped>
.el-card{
    margin: auto auto;
    width: 80%;
    border-radius: 15px;
    margin-top: 20px;
}
</style>