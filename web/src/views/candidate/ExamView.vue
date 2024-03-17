<template>
    <div style="display: flex; justify-content: left;  flex-flow: wrap; padding: 0px 20px;">    <!--一行排不下，flex-flow实现自动换行-->
        <h2 v-if="dataCount===0">数据为空</h2>
        <el-card class="el-card-item" v-for="(item,index) in examStore.examList" :key="index">
            <span>{{ item.displayTitle }}</span><br><br>
            <span>开始时间：{{ item.beginTime }}</span><br>
            <span>结束时间：{{ item.endTime }}</span>
            <div style="float: right; margin-top: 10px;">
                <el-button @click="beginExam(item.testpaperTitle, item.examId)" type="primary" :disabled="btn[item.examId]">{{ btnMsg[item.examId] }}</el-button>                
            </div>
        </el-card>
    </div>
    <el-button type="success" @click="handleAddExam" style="position: absolute; margin-top:3px; margin-left: 45px;">邀请码</el-button>
    <el-pagination class="el-pagination" @current-change="changePage" :pager-count="5" :page-size="6" background layout="prev, pager, next" :total="dataCount" />
</template>

<script lang="ts" setup>
import { useExamStore } from '@/store/exam';
import { ElMessage, ElMessageBox } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const examStore = useExamStore();
const btn = ref<boolean[]>([]);
const btnMsg = ref<string[]>([]);

const dataCount = ref<number>(0);
let currentPage = 1;
function changePage(pageNum:number) {
    examStore.getMyJoinedExam(pageNum, (num)=>{
        init();
        currentPage = pageNum;
        dataCount.value = num;
    });
}
changePage(1);
function handleAddExam(){
    ElMessageBox.prompt('输入应试邀请码：', '应试邀请', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
    }).then(({value})=>{
        if(!value)  return;
        examStore.fillInviteCode(value, ()=>{
            changePage(currentPage)
            ElMessage({type: 'success', message: '成功加入一场应试'});
        })
    }).catch(()=>{
        ElMessage({type: 'info',message: '取消',})
    })
}

function init(){
    examStore.examList.forEach((item)=>{
        let current_date = new Date();
        if(current_date < new Date(item.beginTime))   btnMsg.value[item.examId] = '时间未到', btn.value[item.examId] = true;
        else if(new Date(item.beginTime) < current_date && current_date < new Date(item.endTime))    btnMsg.value[item.examId] = '开始考试', btn.value[item.examId] = false;
        else btnMsg.value[item.examId] = '时间已过', btn.value[item.examId] = true;
    })
}

function beginExam(testpaperTitle:string, examid:number) {
    examStore.tryToJoinExam(examid, (msg:string)=>{
        if(msg != 'success')    {
            ElMessageBox.alert(msg, "信息",{confirmButtonText:'确认',}).catch(()=>{return;});
            return;
        }
        const url = router.resolve({name: 'examination', query:{ title: encodeURIComponent(testpaperTitle), examId: examid }}).href;
        window.open(url);
    });
}

</script>

<style scoped>
.el-card-item{
    background-color: #F0F8FF;
    margin: 5px 15px;
    height: 190px;
    width: 290px;
    border-radius: 12px;
}
.el-pagination{
    position: absolute;
    margin-left: 700px;
}
</style>