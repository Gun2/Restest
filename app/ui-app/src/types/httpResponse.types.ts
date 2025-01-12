
/**
 * 스케줄 결과값
 */
export type HttpResponse = {
    status: string;
    method: string;
    url: string;
    recordTime: string;
    time: number;
    requestHeaderMap: Record<string, string>;
    responseHeaderMap: Record<string, string>;
    requestBody: string;
    responseBody: string;
}