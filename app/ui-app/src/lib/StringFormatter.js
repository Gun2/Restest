
/**
 * string 문자열이 특정 길이를 넘으면 자르고 ...을 뒤에 붙인다.
 * @param str
 * @param index
 * @return {*}
 */
export const strSliceAt = (str, index) => {
    if(str && str.length > index){
        return `${str.slice(0, index)}...`;
    }
    return str;
}

export const toSystemDateFormat = (localDateString) => {
    function padding(num) {
        if(num <= 9){
            return "0" + num;
        }
        return num;
    }
    const date = new Date(localDateString);
    return `${date.getFullYear()}-${padding(date.getMonth()+1)}-${padding(date.getDate())} ${padding(date.getHours())}:${padding(date.getMinutes())}:${padding(date.getSeconds())}`;
}