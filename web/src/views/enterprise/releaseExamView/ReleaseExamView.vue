<template>
    <div style="display: flex; justify-content: center;">
        <el-card class="el-card-out" shadow="never">
            <el-row style="display: flex; justify-content: left; align-items: center; margin-bottom: 20px;">
                <el-card class="el-card-inner" v-for="(item,index) in examStore.examList" :key="index">
                    <span>{{ item.testpaperTitle }}</span><br>
                    <br><span class="span-pure-text">开始时间：</span><br><span style="color: red;">{{ item.beginTime }}</span>
                    <br><span class="span-pure-text">结束时间：</span><br><span style="color: red;">{{ item.endTime }}</span>
                    <br><el-button v-if="item.announced==false" type="primary" size="small" class="announced-msg" @click="releaseExam(item)">发布</el-button>
                    <div v-else class="announced-msg" style="font-size: 14px;">已发布</div>
                </el-card>
            </el-row>
            <el-pagination @current-change="changePage" :page-size="6" small background layout="prev, pager, next" :total="dataCount"/>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { ExamInterface, useExamStore } from '@/store/exam';
import { ref } from 'vue';

const examStore = useExamStore();
const dataCount = ref(0)

function releaseExam(item:ExamInterface){
    examStore.releaseExam(item.examId, ()=>{
        item.announced = true;
    })
}

function changePage(pageNum:number){
    examStore.getAllExam(pageNum, (myDataCount)=>{
        dataCount.value = myDataCount; 
    });
}

changePage(1)

</script>

<style scoped>
.el-card-inner{
    width: 30%;
    height: 200px;
    margin-right: 15px;
    margin-top:10px;
}
.el-card-out{
    width:100%;
    height:520px;
    margin-top:10px;
}
.announced-msg {
    margin-left:70%;
    margin-top:10px;
}
.span-pure-text{
    letter-spacing: 2px;
}
</style>