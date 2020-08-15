from api import app

@app.route('/')
def general():
    return('Documentation available: https://docs.google.com/document/d/1knajfdcsPJY5Hc912C8hMa3jvbpIwsTS1Kt8YQLEbCE/edit')
