<template>
    <el-row style="background-color: white;">
        <el-col :offset="1" :span="11">
            <el-card shadow="never" style="width: 100%; margin-top:25px; margin-bottom:25px; height:500px;">
                应试开始时间：<el-date-picker v-model="beginDateTime" type="datetime" placeholder="Select date and time"/><br><br><br>
                应试结束时间：<el-date-picker v-model="endDateTime" type="datetime" placeholder="Select date and time"/><br><br>
                试&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卷：<el-input class="el-input-pure" v-model="testPaperTitle" disabled/><br>
                备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：<el-input class="el-input-pure" v-model="note"/><br>
                <el-button type="primary" @click="submitExamData">提交数据</el-button>
            </el-card>
        </el-col>
        <el-col :offset="1" :span="10">
            <el-card style="margin-top:25px;" shadow="never">
                <span style="font-size:20px">试卷信息：</span>
                <el-table :data="testPaperStore.testpaperlist" stripe height="400px">
                    <el-table-column prop="id" label="序号" width="60px" />
                    <el-table-column prop="title" label="标题" />
                    <el-table-column width="112">
                        <template #default="scope">
                            <el-button type="primary" @click="lookupTestpaper(scope.row)">查看试卷</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column width="112">
                        <template #default="scope">
                            <el-button :type="type[scope.row.id]" @click="selectTestPaper(scope.row.id, scope.row.title)">{{ btnMsg[scope.row.id] }}</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination @current-change="changePage" background layout="prev, pager, next" :total="pageData.DataCount" />
            </el-card>
        </el-col>
    </el-row>
</template>

<script lang="ts" setup>
import { useTestpaperStore } from '@/store/testpaper'
import { ref } from 'vue';
import $ from 'jquery'
import { useUserStore } from '@/store/user';
import { ElMessage } from 'element-plus';
const testPaperStore = useTestpaperStore();
const note = ref('');
const testPaperTitle = ref('请在左侧选择一张试卷');
const btnMsg = ref<string[]>([]);
const type = ref<string[]>([]);
const beginDateTime = ref('');
const endDateTime = ref('')
const pageData = ref({DataCount: 1, currentPage:1, sumPageCount: 0})
let lastSelectTestPaper = -1;

function formDate(){
    testPaperStore.testpaperlist.forEach((item)=>{  // eslint-disable-next-line
        let idx = (item as any).id;
        type.value[idx] = 'primary', btnMsg.value[idx] = '选择试卷';
    })
}

function selectTestPaper(id:number,title:string){
    testPaperTitle.value = title;
    if(btnMsg.value[id] === '取消选择'){
        btnMsg.value[id] = '选择试卷', lastSelectTestPaper = -1, type.value[id] = 'primary';
        return;
    }
    if(lastSelectTestPaper === -1) {
        lastSelectTestPaper = id;
        btnMsg.value[id] = '取消选择', type.value[id] = 'warning';
    }
    else{
        btnMsg.value[lastSelectTestPaper] = '选择试卷', type.value[lastSelectTestPaper] = 'primary';
        btnMsg.value[id]='取消选择', type.value[id]='warning';
        lastSelectTestPaper = id;
    }
}
function changePage(pageNum:number){
    testPaperStore.getTestPaper(pageNum,(dataCount, sumPageCount)=>{
        pageData.value.DataCount = dataCount, pageData.value.sumPageCount = sumPageCount;
        pageData.value.currentPage = pageNum;
        formDate();
    });
}

changePage(1);
formDate();

function submitExamData(){
    $.ajax({
        url: 'http://127.0.0.1:3000/exam/add/',
        type: 'post',
        headers: {
            Authorization: 'Bearer ' + useUserStore().token,
        },
        data: {
            beginDateTime: beginDateTime.value,
            endDateTime: endDateTime.value,
            note: note.value,
            testPaperTitle: testPaperTitle.value
        },
        success: (resp:string)=>{
            const result = JSON.parse(resp);
            if(result.error_message==='success'){
                ElMessage({message: "成功添加该场应试", type: 'success'});
                testPaperTitle.value = '请在左侧选择一张试卷', note.value = '';
            }
            else    ElMessage.error(result.error_message);
        },
        error: ()=>{
            console.log("error");
        }

    })
}
// eslint-disable-next-line
function lookupTestpaper(obj:any){
    console.log(obj)
}
</script>

<style scoped>
.el-input-pure{
    width: 60%;
    padding-top: 20px;
    padding-bottom: 20px;
}
</style>