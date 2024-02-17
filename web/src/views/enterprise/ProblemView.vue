<template>
    <el-card shadow="never" style="background-color: rgb(251,251,251);">
        <el-table :data="problemStore.problemList" stripe>
            <el-table-column prop="id" label="题号" width="100" />
            <el-table-column prop="title" label="名称" width="400" />
            <el-table-column prop="difficulty" label="难度" width="180" />
            <el-table-column prop="check_by" label="批改" />
            <el-table-column prop="" label="正确率" />
            <el-table-column prop="" label="标签" />
        </el-table>        
    </el-card>

</template>

<script lang="ts" setup>
import { useProblemStore } from '@/store/problem';

const problemStore = useProblemStore();



</script>

<style scoped>

</style>