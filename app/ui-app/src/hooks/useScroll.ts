import { useState, useEffect, useRef } from 'react';

interface ScrollPosition {
    scrollX: number;
    scrollY: number;
}

const useScroll = ()=> {
    const [event, setEvent] = useState<Event | null>(null);

    const ref = useRef<HTMLElement>(null);

    useEffect(() => {
        const element = ref.current;
        if (!element) return;

        const handleScroll = (event: Event) => {
            setEvent(event);
        };

        // 스크롤 이벤트 리스너 추가
        element.addEventListener('scroll', handleScroll);

        // 컴포넌트 언마운트 시 리스너 정리
        return () => {
            element.removeEventListener('scroll', handleScroll);
        };
    }, []);

    return [ref, event];
};

export default useScroll;
