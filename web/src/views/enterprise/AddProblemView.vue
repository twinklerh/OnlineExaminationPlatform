<template>
    <div>试题录入</div>
    <el-form :inline="true" class="el-form">
        <el-form-item label="题目分组：">
            <el-select class="el-select" placeholder="Activity zone" clearable>
                <el-option label="Zone one" value="shanghai" />
                <el-option label="Zone two" value="beijing" />
            </el-select>
        </el-form-item>
        <br>
        <el-form-item label="题目描述：">
            <el-input placeholder="题目描述" clearable />
        </el-form-item>
        
    </el-form>
    
</template>

<script lang="ts" setup>
</script>

<style scoped>
.el-form{
    margin-top: 20px;
    margin-left: 30px;
}
.el-form el-input {
   --el-input-width: 100%;
}
.el-select{
    width: 200px;
}
</style>