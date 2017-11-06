struct AddNumbersRequest {
    int a;
    int b;
};

struct AddNumbersResponse {
    int result;
};

extern "C" int addNumbers(int a, int b) {
    return a + b;
}

extern "C" AddNumbersResponse* addNumbersStruct(AddNumbersRequest* request) {
    AddNumbersResponse* response = new AddNumbersResponse();
    response->result = request->a + request->b;
    return response;
}

extern "C" void freeAddNumbersResponse(AddNumbersResponse* response) {
    delete response;
}