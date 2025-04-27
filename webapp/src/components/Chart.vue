<template>
    <div id="Chart">
        <div class="count">
            <span class="text-xl block">{{ value }}</span>
            <span class="text-s block">{{ desc }}</span>
        </div>
    </div>
</template>
<script>
    export default {
        name: 'Chart',
        props: {
            desc: String,
            value: String
        },
    }
</script>
<style>
#Chart {
    width: 130px;
    height: 100px;
}
.text-s {
    font-size: 14px;
}
.text-xl {
    font-size: 35px;
    font-weight: bold;
    letter-spacing: 0.7px;
}
.count {
    width: 70px;
    height: 50px;
}
</style>
<style scoped>
.block {
    display: block;
}
</style>