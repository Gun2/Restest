import {useEffect, useRef, useState} from 'react';

interface ScrollPosition {
    scrollX: number;
    scrollY: number;
}
type ScrollEvent = UIEvent & {target: {scrollHeight: number; clientHeight: number; scrollTop: number}} | null;

function useScroll<T extends HTMLElement>(): [React.RefObject<T>, ScrollEvent] {
    const [event, setEvent] = useState<ScrollEvent>(null);

    const ref = useRef<T>(null);

    useEffect(() => {
        const element = ref.current;
        if (!element) return;

        const handleScroll = (event: Event) => {
            setEvent(event as ScrollEvent);
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
