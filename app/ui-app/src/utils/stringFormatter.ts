
/**
 * string 문자열이 특정 길이를 넘으면 자르고 ...을 뒤에 붙인다.
 * @param str 문자열
 * @param index 길이
 * @return {*}
 */
export const strSliceAt = (str: string, index : number) => {
    if(str && str.length > index){
        return `${str.slice(0, index)}...`;
    }
    return str;
}


/**
 * 날짜 문자열 값을 공용적으로 사용하는 날짜포맷으로 변경
 * @param localDateString
 */
export const toSystemDateFormat = (localDateString : string) => {
    function padding(num: number) {
        if(num <= 9){
            return "0" + num;
        }
        return num;
    }
    const date = new Date(localDateString);
    return `${date.getFullYear()}-${padding(date.getMonth()+1)}-${padding(date.getDate())} ${padding(date.getHours())}:${padding(date.getMinutes())}:${padding(date.getSeconds())}`;
}