import {css} from 'styled-components';

const colorAdd = (() => {
    const hexToDec = (hex) => {
        return parseInt(hex, 16);
    }

    const decToHex = (dec) => {
        return Number(dec).toString(16);
    }

    const decToHexColor = (dec) => pad(decToHex(colorRange(dec)), 2);

    const pad = (num, pad) => {
        var length = String(num).length;
        for(var i = length; i < pad; i ++){
            num = `0${num}`;
        }
        return num;
    }

    const colorRange = (num) => {
        if(num > 255){
            return 255;
        }else if(num < 0){
            return 0;
        }
        return num;
    }

    return ((color, plus) => {
        color = color.replace("#", "");
        if(color.length === 3) {
            color = color + color;
        }
        var r = hexToDec(color.slice(0,2));
        var g = hexToDec(color.slice(2,4));
        var b = hexToDec(color.slice(4,6));
        r = decToHexColor(r + plus);
        g = decToHexColor(g + plus);
        b = decToHexColor(b + plus);
        
        return `#` + r + g + b;
    });
})();
    

const palette = {
    background : "#212121",
    panel  : "#3C3C3C",
    primary : "#ABABAB",
    secondary : "#8C8C8C",
    text : {
        primary : "#ABABAB",
        secondary : "#8C8C8C",
        default : "#e4e4e4",
    },
    point : "#B38AF8",
    button : {
        primary : "#337ab7",
        danger : "#d9534f",
        warning : "#eb9c2d",
        info : "#5bc0de",
        success : "#5cb85c",
        default : "#ffffff",
        panel : "#3C3C3C",
    },
    status : {
        success : "#2fcc71",
        failure : "#e74c3c",
        default : "#ABABAB",
        warning : "#eb9c2d",
    },
    disabled: "#797979",
    validation: "#d9534f",
    shadow: "#000000aa",
};

const style = {
    readonly : css`
        pointer-events:none;
        background-color : ${palette.disabled}; 
    `
}

const flex = {
    startCenter : css`
        display: flex;
        align-items: center;
        justify-content: flex-start;
    `,
    endCenter : css`
        display: flex;
        align-items: center;
        justify-content: flex-end;
    `,
    center : css`
        display: flex;
        align-items: center;
        justify-content: center;
    `,
    aroundCenter : css`
        display: flex;
        align-items: center;
        justify-content: space-around;
    `,
    middle : css`
    display: flex;
    align-items: center;
    `,
}

const backgroundCover = `
    background-color : #00000055;
    width:100%;
    height:100%;
    position:absolute;
    display:flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
`

const map = {
    button : (theme) => `
        background-color : ${palette.button[theme]};
        &:focus,
        &:hover{
            background-color : ${colorAdd(palette.button[theme], 50)}
        }

        &:active{
            background-color : ${colorAdd(palette.button[theme], -10)}
        }
    
        &:disabled {
            cursor: default;
            opacity: 0.7;
            background-color : ${colorAdd(palette.button[theme], -150)}
        }
    `,
    tab : (theme) => `
        background-color : ${palette.button[theme]};
        &:hover{
            background-color : ${colorAdd(palette.button[theme], 50)}
        }
        &:disabled {
            cursor: default;
            opacity: 0.7;
            background-color : ${colorAdd(palette.button[theme], -150)}
        }
    `
}

const theme = {
    palette,
    map,
    flex,
    colorAdd,
    style,
    backgroundCover
};
export default theme;