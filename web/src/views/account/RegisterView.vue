<template>
    <div class="el-father">
        <el-card class="el-card">
            <template #header>
                <div class="card-header">
                    <span class="text-span">注册</span>
                </div>
            </template>
            <div class="el-body">
                <el-form>
                    <el-input v-model="account_id" placeholder="用户名" class="el-input" clearable />
                    <el-input type="password" v-model="password" placeholder="密码" class="el-input" />
                    <el-input type="password" v-model="confirmPassword" placeholder="确认密码" class="el-input confirm" />
                </el-form>
                <span class="error-msg"> &nbsp;{{ error_message }}</span>
                <el-button class="el-button" type="primary" @click="register">注&nbsp;册</el-button>
            </div>
            <span style="font-size: 13px; margin-left: 280px; font-weight: lighter;">
                已有账号，去<RouterLink :to="{name: 'login'}" style="text-decoration: none; color:blue">登陆</RouterLink>
            </span>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import $ from 'jquery';
import { useRouter } from 'vue-router';

const router = useRouter();
let error_message = ref('')
let account_id = ref('');
let password = ref('')
let confirmPassword = ref('')

function register(){
    $.ajax({
        url: 'http://localhost:3000/account/register/',
        type: "post",
        data: {
            account_id: account_id.value,
            password: password.value,
            confirmPassword: confirmPassword.value,
        },// eslint-disable-next-line
        success(resp:any) {
            error_message.value = resp.error_message;
            if(error_message.value === "register successfully!")    {
                alert("注册成功！")
                router.push({name:'login'});
            }
        },
        error() {
            console.log("register error");
        }
    })
}


</script>

<style scoped>
.el-father{
    background-color: rgb(246,248,251);
    height: 100vh;
    display: flex;
}
.el-card{
    width: 35%;
    height: 90vh;
    margin: 16px auto;
}
.el-body{
    margin-left: 20px;
    margin-right: 20px;
}
.el-input{
    margin-bottom: 20px;
    height: 35px;
}
.text-span{
    font-size: 24px;
    font-weight: lighter;
}
.confirm{
    margin-bottom: 0px;
}
.el-button {
    width: 75%;
    display: flex;
    margin: 10px auto;
}
.error-msg{
    color: red;
    font-size: 13px;
}
</style>