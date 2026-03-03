import sys
from scapy.all import sniff

# Define the packet handler function
def packet_handler(packet):
    print(packet.show())

# Capture packets on the 'eth0' interface
sniff(iface="eth0", prn=packet_handler)
