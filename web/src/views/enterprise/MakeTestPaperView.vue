<template>
    <el-row class="el-row-select">
        <el-input class="el-input" v-model="inputdata" placeholder="输入试题名称查询试题" clearable/>
        <el-button type="primary" class="el-button-select" @click="selectProblem">搜&nbsp;索</el-button>
    </el-row>
    <el-card v-if="result_form_visable" shadow="never" class="el-card">
        <el-table ref="multipleTableRef" @selection-change="handleSelectionChange" :data="resultlist" stripe class="el-table">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="题号" width="125" align="center" sortable/>
            <el-table-column prop="title" label="名称" width="250" />
            <el-table-column prop="type" label="题型" width="90" sortable/>
            <el-table-column prop="checkBy" label="批改" width="100" />
            <el-table-column prop="difficulty" label="难度" width="100" align="center" sortable/>
            <el-table-column prop="" label="正确率" width="180" />
            <el-table-column width="180">
                <template #default="{row}">
                    <el-button type="primary" @click="handleButtonClick(row)">查看</el-button>
                    <el-popover :visible="visible[row.id]" placement="right" :width="160" trigger="click">
                        <p>确定要删除吗？</p>
                        <div style="text-align: right; margin: 0">
                            <el-button size="small" type="success" @click="visible[row.id]=false">取消</el-button>
                            <el-button size="small" type="danger" @click="handleButtonDelete(row.id)">确认</el-button>
                        </div>
                        <template #reference>
                            <el-button type="danger" @click="handlePrimaryDelete(row.id)">删除</el-button>
                        </template>
                    </el-popover>
                </template>
            </el-table-column>
        </el-table>
        <el-col :offset="20">
            <el-button type="success" @click="addtotestpaper">添加到试卷</el-button>
            <el-button type="primary" @click="result_form_visable=false;" style="margin-right: 12px; margin-top: 12px;">关闭搜索结果</el-button>            
        </el-col>
    </el-card>
</template>

<script lang="ts" setup>
import { ProblemInterface, useProblemStore } from '@/store/problem';
import { ref, toRaw } from 'vue';
import router from '@/router';
import { ElTable } from 'element-plus';
const inputdata = ref('');
const result_form_visable = ref(false);
const resultlist = ref<ProblemInterface[]>([]);
const visible = ref([false]);
const problemStore = useProblemStore();
let last_row = 0;
function handlePrimaryDelete(problem_id:number){
    visible.value[last_row] = false;
    visible.value[problem_id] = true;
    last_row = problem_id;
}
function handleButtonDelete(problem_id:number){
    visible.value[problem_id] = false;
    problemStore.deleteProblem(problem_id)
}
function handleButtonClick(row: ProblemInterface){
    const url = router.resolve({ name: 'problemdetail', query: { id: row.id },}).href;
    window.open(url, '_blank');
}

function selectProblem(){
    result_form_visable.value = true;
    problemStore.getProblemByTitle(inputdata.value, (list)=>{
        if(list == null)    return;
        resultlist.value = list;
        console.log(resultlist.value, list)
    });
}

const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref<ProblemInterface[]>([])   //  存储选中的数据
const handleSelectionChange = (val: ProblemInterface[]) => multipleSelection.value = val;
function addtotestpaper(rows ?: ProblemInterface[]){
    if (rows) {
    rows.forEach((row) => {
      // TODO: improvement typing when refactor table
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-expect-error
      multipleTableRef.value!.toggleRowSelection(row, undefined)
    })
  } else {  //  rows是个可选参数，else对应未传入参数时间的情形。! 是非空断言操作符（Non-null assertion operator）。它告诉 TypeScript 编译器，你确信某个表达式的值不会是 null 或 undefined，并请求 TypeScript 在此表达式上放宽空值检查。
    multipleTableRef.value!.clearSelection()
  }
}

</script>

<style scoped>
.el-row-select {
    display: flex; 
    justify-content: center; 
    align-items: center; 
    height: 65px;
    background-color: white !important;
}
.el-input{
    width: 500px;
    height: 45px;
    font-size: 17px;
}
.el-input :deep(.el-input__wrapper){
    border-radius: 15px 0px 0px 15px;
}
.el-button-select{
    height: 45px;
    font-size: 17px;
    border-radius: 0px 15px 15px 0px;
}
.el-card{
    display: flex;
    background-color: white;
    margin-top:10px;
    border-radius: 19px;
}
.el-card :deep(.el-card__body){
    width: 100%;
}
.el-table{
    width: 100%;
}
.el-table :deep(.el-table__header-wrapper){
    width: 100%;
}
</style>