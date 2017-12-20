What we do here in Gdansk focuses on APM (Application Performance Management). Curious if assuring performance is your kind of thing? Here’s a way to find out and gain some extra points in the recruitment process. Complete this task and we’ll be impressed:

**TASK DEFINITION:**

Pick a technology used in web applications development. 
Figure out what's worth looking at from the application performance point of view.
Think of some performance metrics. Write a script (python would be nice) or program (java/c++) 
that collects your metrics every minute and stores them for some period of time.
Add a CSV export capability, present the results in a spread sheet or even present as a chart on a web page.
Be ready to answer questions about the implementation and your choice of metrics.

**SPECIFICATION:**

Technology used - Spring, Chart.js (D3?)

Metrics:
FULL REQUEST TIME:
    SpringControllerLogger.measureControllerTime

QUERY TIME/SEND TIME
    ?    
    
RESPONSE SIZE
    ?

ENDPOINT
source (mobile/web)
Platform? System? Browser?
IP source (country, client)
    SpringControllerLogger.logClients    

HTTP STATUS PER ENDPOINT
    ?

EXCEPTIONS
    SpringControllerLogger.logExceptions
    
CPU/memory usage over time
    MemoryChecker.checkMemoryState