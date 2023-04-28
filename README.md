# Rentmanager

##   Contraintes appliquées:

- un client n'ayant pas 18ans ne peut pas être créé
- un client ayant une adresse mail déjà prise ne peut pas être créé
- le nom et le prénom d'un client doivent faire au moins 3 caractères
- une voiture ne peux pas être réservé 2 fois le même jour
- une voiture ne peux pas être réservé plus de 7 jours de suite par le même 
- une voiture ne peux pas être réservé 30 jours de suite sans pause
- si un véhicule est supprimé, alors il faut supprimer les réservations associées
- une voiture doit avoir un constructeur, son nombre de place doit être compris entre 2 et 9

Il est également possible de supprimer un client, véhicule ou réservation.

Les contraintes sont visibles avec un ServiceException

##  Axes d'amélioration
- implémenter modification et détails
- remplacer les exceptions par des pop-up