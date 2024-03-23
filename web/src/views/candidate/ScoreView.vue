<template>
    <div class="container">
        <el-row style="margin-top:15px;">
            <el-col :span="9" :offset="1"> 
                <span class="pure-text">试卷名称：</span>
                <el-input v-model="testPaperTitle" size="large" style="width: 75%;" clearable/>
            </el-col>
            <el-button type="primary" @click="selectGrade" style="margin-left: 10px;">查询</el-button>
        </el-row>
    </div>
    <el-table :data="data" table-layout="auto" height="300" stripe style="width:90%; margin: 20px 30px;">
        <el-table-column width="90px" prop="testPaperId" label="场次"></el-table-column>
        <el-table-column width="230px" prop="testPaperTitle" label="标题"></el-table-column>
        <el-table-column width="180px" prop="beginTime" label="开始时间"></el-table-column>
        <el-table-column width="180px" prop="endTime" label="结束时间"></el-table-column>
        <el-table-column width="100px" prop="candidateName" label="参与者"></el-table-column>
        <el-table-column prop="score" label="成绩" sortable></el-table-column>
    </el-table>
    <el-pagination small @current-change="changePage" :page-size="7" :total="dataCount" layout="prev, pager, next" background/>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
import $ from 'jquery';
import { ElMessage } from 'element-plus';
 
const dataCount = ref(1);
const testPaperTitle = ref("");
const data = ref();

function changePage(page:number)   {
    $.ajax({
        url: 'http://127.0.0.1:3000/candidate/get/grade/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        data: {
            page: page,
            testPaperTitle: testPaperTitle.value,
        },
        success: (result: string) => {
            const resp = JSON.parse(result);
            if(resp.error_message === 'success')    {
                data.value = JSON.parse(resp.jsonArray);
                dataCount.value = parseInt(resp.dataCount);                
            }   else    {
                dataCount.value = 0;
                data.value = []
            }
        },
        error: ()=>{
            ElMessage.error("失败");
        }
    })
}
function selectGrade()  {
    changePage(1);
}
</script>

<style scpoed>
</style>