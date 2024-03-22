<template>
    <h2 style="display: flex; justify-content: center;">{{ subStringResult }}</h2>
    <el-divider />
    <div class="container">
        <div style="position: fixed; height: 530px;">
            <el-scrollbar max-height="530px">
                <CheckTitleCardComp v-for="(item,index) in bindTitleData" :index="index" :key="index" @click="handleClick(item)">
                    <template #title>
                        <div class="title">{{ item.problemId +"、"+item.description }}</div>
                    </template>
                </CheckTitleCardComp>
            </el-scrollbar>
        </div>
    </div>
    <div class="main">
        <div style="width: 1048px;">题干：{{ activeProblem }}</div><br>
        <div>
            <el-row>
                <el-col :span="5">答题人</el-col>
                <el-col :span="9">答题人的答案</el-col>
                <el-col :span="4">应得分数</el-col>
                <el-col :span="4"><div style="display: flex; justify-content: center;">本题分值</div></el-col>
            </el-row>
            <el-row :class="index%2===0 ? 'mod-res-one': 'mod-res-two'" v-for="(item,index) in activeObject" :key="index">
                <el-col :span="5">
                    {{ item.candidateName }}
                </el-col>
                <el-col :span="9">
                    {{ item.answer }}
                </el-col>
                <el-col :span="4">
                    <el-input v-model="item.getScore" :class="index%2===0 ? 'mod-res-one-input': 'mod-res-two-input'" size="default" style="display: inline; width: 15px;" placeholder="在此输入分数" />
                </el-col>
                <el-col :span="4">
                    <div style="display: flex; justify-content: center;">{{ item.score }}</div>
                </el-col>
            </el-row><br>
            <el-button type="primary" @click="submit">提交本题</el-button>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { useRoute } from 'vue-router';
import $ from 'jquery';
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
import CheckTitleCardComp from '@/components/CheckTitleCardComp.vue';
import { ElMessageBox } from 'element-plus';
const route = useRoute();
const testpaperTitle = decodeURIComponent(route.query.title as string) as string;
const subStringResult = testpaperTitle.substring(testpaperTitle.indexOf('}') + 1, testpaperTitle.length);
interface CheckedDataInterface  {   problemId: string, description: string, checkId: number, answer:string, candidateName: string, getScore: number, score: number, }
const CheckData = ref<CheckedDataInterface[]>([]);
const bindTitleData = ref<CheckedDataInterface[]>([])
const activeProblem = ref('');
const activeObject = ref<CheckedDataInterface[]>([])
function getCheckMsg()  {
    $.ajax({
        url: 'http://127.0.0.1:3000/enterprise/get/check/msg/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        data: {
            testpaper_title: testpaperTitle,
        },
        success: (result:string) => {
            console.log(result)
            const resp = JSON.parse(result);
            CheckData.value = resp;
            filterateProblemTitle();
        },
        error: () => {
            console.log("error");
        }
    })
}
getCheckMsg();

function filterateProblemTitle() {//取出标题并去重
    let list = ['']
    CheckData.value.forEach((value)=> {
        if(!list.includes(value.problemId))  {    //  如果不包含
            bindTitleData.value.push(value);     //  标题不插入，题干插入
            list.push(value.problemId);
        }
        if(!activeProblem.value)    {
            activeProblem.value = value.problemId +'、' +value.description;
            handleClick(value)
        }
    })
}

function handleClick(item:CheckedDataInterface)  {
    activeObject.value = [];
    const problemId = item.problemId;
    activeProblem.value = item.problemId +"、" + item.description;
    CheckData.value.forEach((value) => {
        if(value.problemId === problemId)   {
            activeObject.value.push(value);
        }
    })
}

function submit() {
    let listJson:{[key: number]: number} = {}
    activeObject.value.forEach((value)=>{
        const checkId = value.checkId;
        const getScore = value.getScore;
        listJson[checkId] =  getScore;
    })
    $.ajax({
        url: 'http://127.0.0.1:3000/enterprise/submit/check/result/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        data: {
            activeObject: listJson,
        },
        success: (result:string)=>{
            const resp = JSON.parse(result);
            if(resp.error_message === 'success')    {
                ElMessageBox.alert('成功', '提交成功').catch(()=>{return;})
            }
        },
        error: ()=>{
            console.log("error");
        }
    })
}
</script>

<style scoped>
.container {
    width: 350px;
    position: relative;
}
div.title {
    padding-left: 15px;
}
.main {
    margin-left: 380px;
    position: absolute;
}
.mod-res-one {
    background-color: #FFEBCD;
}
.mod-res-two {
    background-color: #FFDEAD;
}
.mod-res-one-input :deep(.el-input__inner) {
    background-color: #FFEBCD !important;
}
.mod-res-one-input :deep(.el-input__wrapper) {
    background-color: #FFEBCD !important;
}
.mod-res-two-input :deep(.el-input__inner) {
    background-color: #FFDEAD !important;
}
.mod-res-two-input :deep(.el-input__wrapper) {
    background-color: #FFDEAD !important;
}
</style>