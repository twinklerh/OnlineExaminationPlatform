<template>
    <div style="padding-top: 20px;">
        <el-steps :active="activeNumber" finish-status="success" process-status="finish" align-center>
            <el-step title="Step 1" description="输入试卷的简单信息" />
            <el-step title="Step 2" description="移动试题到试卷" />
            <el-step title="Step 3" description="确认试卷信息" />
        </el-steps>
    </div>
    <div v-if="activeNumber===0" style="margin-left: 280px; margin-top:60px">
        <el-form>
            <el-form-item label="试卷标题：">
                <el-input class="el-input-1" v-model="paper.title" placeholder="试卷标题" clearable />
            </el-form-item>
            <el-form-item label="备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：">
                <el-input class="el-input-1" v-model="paper.memo" placeholder="备注信息" clearable />
            </el-form-item>
        </el-form>
    </div>
    <div v-if="activeNumber===1" style="display: flex; justify-content: center; align-items: center; ">
        <el-transfer v-model="targetData" :data="sourceData" target-order="unshift" :titles="['Source', 'Target']" filterable filter-placeholder="输入试题名查找" />
    </div>
    
    <div v-if="activeNumber===2" style="margin-left: 280px; margin-top:60px">
        <el-form>
            <el-form-item label="试卷标题：">
                <span>{{ paper.title }}</span>
            </el-form-item>
            <el-form-item label="备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：">
                <span>{{ paper.memo }}</span>
            </el-form-item>
            <el-form-item label="试题数目：">
                <span>{{ paper.problemCount }} 题</span>
            </el-form-item>
        </el-form>
    </div>

    <div style="display: flex; flex-direction: row-reverse; width: 85%; margin-top:5px">
        <el-button type="primary" @click="nextStep" style="margin-left: 10px;">下一步</el-button>
        <el-button type="success" v-if="activeNumber" @click="lastStep">上一步</el-button>
    </div>
    
</template>

<script lang="ts" setup>
import { reactive, ref, } from 'vue'
import { TestpaperInterface, useTestpaperStore } from '@/store/testpaper';
import { useProblemStore,ProblemInterface } from '@/store/problem';
import { rawData_to_sourceData, Option, sortProblem } from '@/ts/handleTransferData';
import { ElMessage } from 'element-plus';

const paper = reactive<TestpaperInterface>({ title: '', memo: '', problemCount: 0 });
const activeNumber = ref(0)
const problemStore = useProblemStore();
const testpaperStore = useTestpaperStore();

const targetData = ref<Option[]>([]);
const sourceData = ref<Option[]>([]);
const beforeSubmitData = ref<ProblemInterface[]>([]);

function init(){
    problemStore.getProblemList(()=>{
        sortProblem(problemStore.problemList);
        sourceData.value = rawData_to_sourceData(problemStore.problemList);
    })
}
init();

const lastStep = () =>{
    if(activeNumber.value !== 0)  activeNumber.value--;
}

const nextStep = () => {
    if(activeNumber.value < 3) activeNumber.value++;
    if(activeNumber.value === 1 && paper.title==='') {
        ElMessage.error("试卷标题不能为空");
        activeNumber.value--;
    }
    if(activeNumber.value === 2) {
        moveProblem();
        if(paper.problemCount === 0){
            ElMessage.error("试题为空")
            activeNumber.value--;
        }
    }
    if(activeNumber.value === 3) submit();
}

function moveProblem(){
    const targetvalues:number[] = [];
    for(const item of targetData.value){
        // eslint-disable-next-line
        // @ts-ignore
        targetvalues.push(item);
        paper.problemCount++;
    }
    sourceData.value.forEach(item=>{
        if(targetvalues.includes(item.key))     beforeSubmitData.value.push(item.initial);
    })
}

function submit(){
    let problemsString = "";
    beforeSubmitData.value.forEach(item=>{
        let obj = {
            problemId: item.id,
            description: item.description,
            rightAnswer: item.rightAnswer,
            checkBy: item.checkBy,
            type: item.type,
        };
        problemsString = problemsString + JSON.stringify(obj);
    })
    testpaperStore.submitTestPaper(paper, problemsString, (msg)=>{
        const resp = JSON.parse(msg);
        if(resp.error_message === 'success'){
            paper.problemCount = activeNumber.value = 0;
            ElMessage({message: msg, type: 'success'});
            paper.memo = paper.title = '';
            targetData.value = [];
            init();
            beforeSubmitData.value = [];
        }
        else    ElMessage.error(resp.error_message);
    });
}
</script>

<style scoped>
.el-input-1{
    width: 500px;
}
</style>