<template>
    <el-card shadow="never" style="background-color: white; margin-top:10px;border-radius: 19px;">
        <el-table :data="problemStore.problemList" stripe>
            <el-table-column prop="id" label="题号" width="100" align="center"/>
            <el-table-column prop="title" label="名称" width="250" />
            <el-table-column prop="type" label="题型" width="70" />
            <el-table-column prop="check_by" label="批改" width="100"/>
            <el-table-column prop="difficulty" label="难度" width="90" align="center" />
            <el-table-column prop="" label="正确率" />
            <el-table-column>
                <template #default="{row}">
                    <el-button type="primary" @click="handleButtonClick(row)">查看</el-button>
                </template>
            </el-table-column>
        </el-table>        
    </el-card>

</template>

<script lang="ts" setup>
import { useProblemStore, ProblemInterface } from '@/store/problem';

const problemStore = useProblemStore();

problemStore.getProblemList();

function handleButtonClick(row:ProblemInterface){
    console.log(row)
}

</script>

<style scoped>

</style>