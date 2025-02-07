import axios, {AxiosResponse} from "axios";
import {SuccessResponse} from "types/api.types";
import {HttpResponse} from "types/httpResponse.types";

export const readAllResponseSuccess = (schedulerId: number): Promise<AxiosResponse<SuccessResponse<HttpResponse[]>>> => axios.get(`/api/v1/scheduler/${schedulerId}/responses/successes`);
export const readAllResponseFailure = (schedulerId: number): Promise<AxiosResponse<SuccessResponse<HttpResponse[]>>> => axios.get(`/api/v1/scheduler/${schedulerId}/responses/failures`);
