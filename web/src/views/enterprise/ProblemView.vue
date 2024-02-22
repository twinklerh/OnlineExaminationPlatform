<template>
    <el-card shadow="never" class="el-card">
        <el-table :data="problemStore.problemList" stripe class="el-table">
            <el-table-column prop="id" label="题号" width="100" align="center"/>
            <el-table-column prop="title" label="名称" width="250" />
            <el-table-column prop="type" label="题型" width="70" />
            <el-table-column prop="check_by" label="批改" width="100"/>
            <el-table-column prop="difficulty" label="难度" width="90" align="center" />
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
    </el-card>

</template>

<script lang="ts" setup>
import { useProblemStore, ProblemInterface } from '@/store/problem';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const problemStore = useProblemStore();
const visible = ref([false]); 
let last_row = 0;


problemStore.getProblemList();

function handleButtonClick(row: ProblemInterface){
    const url = router.resolve({ name: 'problemdetail', query: { id: row.id },}).href;
    window.open(url, '_blank');
}

function handlePrimaryDelete(problem_id:number){
    visible.value[last_row] = false;
    visible.value[problem_id] = true;
    last_row = problem_id;
}

function handleButtonDelete(problem_id:number){
    visible.value[problem_id] = false;
    problemStore.deleteProblem(problem_id)
}

</script>

<style scoped>
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