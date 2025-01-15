import React, {useCallback, useState} from 'react';
import styled from "styled-components";
import Tab from "../../atoms/Tab";

const Box = styled.div`
    display : flex;
    flex-direction : column;
`;
const Head = styled.div`
    ${({theme}) => theme.flex.startCenter});
    gap : 1px;
`;

const Content = styled.div`

`

type TabData = {
    // 탭의 이름
    key: string;
    // 탭 클릭 시 표시될 내용
    content: React.ReactNode;
};

type TabBarProps = {
    tabData: Array<TabData>;
}

function TabBar(
    {
        tabData = []
    }: TabBarProps
) {
    const [activeTab , setActiveTab] = useState<number>(0);
    const onTab = useCallback((key: number) => {
        setActiveTab(key);
    }, []);

    return (
        <Box>
            <Head>
                {tabData.map((data, i) => (
                    <Tab onTab={onTab} key={i} item={i} active={activeTab==i}>{data.key}</Tab>
                ))}
            </Head>
            <Content>
                {tabData[activeTab].content}
            </Content>
        </Box>
    );
}

export default TabBar;